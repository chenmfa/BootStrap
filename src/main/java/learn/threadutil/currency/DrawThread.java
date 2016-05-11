package learn.threadutil.currency;

public class DrawThread extends Thread {

	private String name; // 操作人
    private Account account; // 账户
    private int x; // 存款金额

    DrawThread(String name, Account account, int x) {
        this.name = name;
        this.account = account;
        this.x = x;
    }

    public void run() {
        account.drawing(x, name);
    }
}
