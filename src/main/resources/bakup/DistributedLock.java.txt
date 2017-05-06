package com.stt.redis;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.cache.util.CacheUtils.CacheCallback;
import com.lefu.commons.cache.util.CacheUtils.Redis;
import com.lefu.commons.utils.io.JdkSerializeUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.pay.common.util.DateUtil;

public class DistributedLock {
	private static Long timeOutValue = 50000L;
	public static final String countKeyString = "_COUNT";

	private static Logger logger = LoggerFactory.getLogger(DistributedLock.class);

	public static boolean isHaveLock(final String lockKey) {
		Long isLockFlag = setnx(lockKey);
		if (isLockFlag == 1) {// 说明获取到了锁
			return true;
		} else {
			String timeValueStr = (String) JdkSerializeUtils
					.deserialize(CacheUtils.get(null, JdkSerializeUtils.serialize(lockKey)));
			if (timeValueStr == null) { // 说明锁已经被释放
				if (setnx(lockKey) == 1)
					return true;
				else
					return false;
			}
			// 判断是否锁超时
			Long timeValue = Long.valueOf(timeValueStr);
			// 服务器时间需要同步
			if (System.currentTimeMillis() - timeValue > timeOutValue) { // 加锁时间超过限制，开始重置锁,返回true说明已经获得锁
				return resetLockFlag(Long.valueOf(timeValue), lockKey);
			}

		}
		return false;
	}

	public static boolean resetLockFlag(final Long flagTime, final String lockKey) {
		String timeStampStr = String.valueOf(System.currentTimeMillis());

		String lockOldTime = getSet(lockKey, timeStampStr);
		if (lockOldTime == null)
			return true; // 为空说明获得锁标记
		else if (Long.valueOf(lockOldTime).longValue() == flagTime.longValue())
			return true; // 获取到的值不等于原有get的值，说明锁被别的线程重置
		else
			return false;
	}

	public static void delLockFlag(final String lockKey) {
		CacheUtils.execute(null, new CacheCallback<Long>() {
			@Override
			public Long doCallback(com.lefu.commons.cache.util.CacheUtils.Redis redis) {

				return redis.del(JdkSerializeUtils.serialize(lockKey));
			}
		});
	}

	public static String getSet(final String lockKey, final String timeStampStr) {
		String timeValueString = CacheUtils.execute(null, new CacheCallback<String>() {
			@Override
			public String doCallback(com.lefu.commons.cache.util.CacheUtils.Redis redis) {
				byte[] result = redis.getSet(JdkSerializeUtils.serialize(lockKey),
						JdkSerializeUtils.serialize(timeStampStr));
				if (JdkSerializeUtils.deserialize(result) == null)
					return null;
				else
					return (String) JdkSerializeUtils.deserialize(result);
			}
		});
		return timeValueString;
	}

	public static Long setnx(final String lockKey) {
		return CacheUtils.execute(null, new CacheCallback<Long>() {
			@Override
			public Long doCallback(com.lefu.commons.cache.util.CacheUtils.Redis redis) {
				return redis.setnx(JdkSerializeUtils.serialize(lockKey),
						JdkSerializeUtils.serialize(String.valueOf(System.currentTimeMillis())));
			}
		});
	}

	public static boolean countPlusOnLock(final String lockKey) {
		String countKey = lockKey + countKeyString;

		if (isHaveLock(lockKey)) {
			try {
				Integer count = CacheUtils.get(countKey, Integer.class);
				count += 1;
				CacheUtils.set(countKey, count, false);
				return true;
			} finally {
				DistributedLock.delLockFlag(lockKey);
			}
		} else {
			return false;
		}
	}

	public static boolean countSubtractionOnLock(final String lockKey) {
		String countKey = lockKey + countKeyString;

		if (isHaveLock(lockKey)) {
			try {
				Integer count = CacheUtils.get(countKey, Integer.class);
				count -= 1;
				CacheUtils.set(countKey, count, false);
				return true;
			} finally {
				DistributedLock.delLockFlag(lockKey);
			}
		} else {
			return false;
		}
	}

	public static boolean countConfig(final String lockKey, Integer count) {
		String countKey = lockKey + countKeyString;
		if (isHaveLock(lockKey)) {
			try {
				CacheUtils.set(countKey, count, false);
			} finally {
				DistributedLock.delLockFlag(lockKey);
			}
			return true;
		} else {
			return false;
		}
	}

	public static Set<byte[]> keys(final String statisticsKey) {
		return CacheUtils.execute(null, new CacheCallback<Set<byte[]>>() {
			@Override
			public Set<byte[]> doCallback(Redis redis) {
				return redis.keys(StringUtils.serialize(statisticsKey, CacheUtils.DEFAULT_CHARSET));
			}
		});
	}

	public static Integer getCountTotal(String lockKey) {
		final String key = lockKey + "_limit_*";
		final Set<byte[]> set = DistributedLock.keys(key);

		if (0 == set.size())
			return 0;

		List<byte[]> list = CacheUtils.execute(null, new CacheCallback<List<byte[]>>() {
			@Override
			public List<byte[]> doCallback(Redis redis) {
				return redis.mget(set.toArray(new byte[set.size()][]));
			}
		});

		// logger.info("keys size:" + list.size());
		int count = 0;
		for (byte[] bs : list) {
			count += ((Number) JdkSerializeUtils.deserialize(bs)).intValue();
			// logger.info("key:" + StringUtils.deserialize(bs,
			// CacheUtils.DEFAULT_CHARSET) + ";count:" + count);
		}
		return count;
	}

	public static void incr(String lockKey, int time) {
		final String statisticsKey = lockKey + "_limit_" + DateUtil.formatNow("MMddHHmm");

		Integer count = CacheUtils.get(statisticsKey, Integer.class);
		// logger.info("before count:" + count);
		if (null == count)
			CacheUtils.setEx(statisticsKey, 1, time);
		else {
			Long ttl = CacheUtils.execute(null, new CacheCallback<Long>() {
				@Override
				public Long doCallback(Redis redis) {
					// TODO Auto-generated method stub
					return redis.ttl(StringUtils.serialize(statisticsKey, CacheUtils.DEFAULT_CHARSET));
				}
			});
			CacheUtils.setEx(statisticsKey, ++count, ttl.intValue());
		}
		// logger.info("after count:" + CacheUtils.get(null,
		// StringUtils.serialize(statisticsKey, CacheUtils.DEFAULT_CHARSET)));
	}

	public static Integer getCountValue(final String lockKey) {
		String countKey = lockKey + countKeyString;
		return CacheUtils.get(countKey, Integer.class);
	}

	public static void setCountValue(final String lockKey, Integer value) {
		String countKey = lockKey + countKeyString;
		CacheUtils.set(countKey, value);
	}
}
