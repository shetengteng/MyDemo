package com.stt.ThreadDemo.ThreadPattern.part00;

public class Test01_06 {

	public static void main(String[] args) {
		
	
		
		
	}
	
	
	public class Bank {
		private int money;
		private String name;
		
		public Bank(String name,int money){
			this.money = money;
			this.name = name;
		}
		
		//存款
		public synchronized void deposit(int m){
			money += m;
		}
		
		public synchronized boolean withdraw(int n){
			if(n < money){
				money -= n;
				return true;//已取款
			}else{
				return false;//余额不足
			}
		}
		
		public String getName(){
			return name;
		}
	}
	
}
