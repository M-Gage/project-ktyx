package com.ywkj.ktyunxiao.admin;

import com.ywkj.ktyunxiao.dao.GoodsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ApplicationTests {




	@Test
	public void threadTest() {
		Account account = new Account();
		ExecutorService service = Executors.newFixedThreadPool(10);
		for(int i = 1; i <= 100; i++) {
			service.execute(new AddMoneyThread(account, 1));
		}

		service.shutdown();

		while(!service.isTerminated()) {}

		System.out.println("账户余额: " + account.getBalance());
	}


	class Account {
		private double balance;     // 账户余额

		/**
		 * 存款
		 * @param money 存入金额
		 */
		public synchronized void deposit(double money) {
			double newBalance = balance + money;
			try {
				Thread.sleep(10);   // 模拟此业务需要一段处理时间
			}
			catch(InterruptedException ex) {
				ex.printStackTrace();
			}
			balance = newBalance;
		}

		/**
		 * 获得账户余额
		 */
		public double getBalance() {
			return balance;
		}
	}


	class AddMoneyThread implements Runnable {
		private Account account;    // 存入账户
		private double money;       // 存入金额

		public AddMoneyThread(Account account, double money) {
			this.account = account;
			this.money = money;
		}

		@Override
		public void run() {
			synchronized (account){
				account.deposit(money);
			}
		}

	}

}
