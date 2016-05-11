package learn.threadutil.currency;

public class SaveThread extends Thread {

	private String name; // 操作人
    private Account account; // 账户
    private int x; // 存款金额

    SaveThread(String name, Account account, int x) {
        this.name = name;
        this.account = account;
        this.x = x;
    }

    public void run() {
        account.saving(x, name);
    }
}
