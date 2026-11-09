/** A savings account: a bank account that earns interest */
public class SavingsAccount extends BankAccount
{
	/** annual interest rate (2.5%) */
	private double rate = 0.025;
	/** identifies this savings account among those sharing one account number */
	private int savingsNumber = 0;
	/** hides the accountNumber inherited from BankAccount */
	private String accountNumber;

	/** standard constructor
	@param name the owner of the account
	@param amount the beginning balance */
	public SavingsAccount(String name, double amount)
	{
		super(name, amount);
		accountNumber = super.getAccountNumber() + "-" + savingsNumber;
	}

	/** copy constructor: creates another savings account for the same owner
	@param oldAccount the savings account to copy
	@param amount the beginning balance of the new account */
	public SavingsAccount(SavingsAccount oldAccount, double amount)
	{
		super(oldAccount, amount);
		savingsNumber = oldAccount.savingsNumber + 1;
		accountNumber = super.getAccountNumber() + "-" + savingsNumber;
	}

	/** calculates one month's interest on the balance and deposits it */
	public void postInterest()
	{
		double monthlyInterest = getBalance() * (rate / 12);
		deposit(monthlyInterest);
	}

	/** overrides getAccountNumber to return the hiding accountNumber
	@return the account number with the savings suffix */
	@Override
	public String getAccountNumber()
	{
		return accountNumber;
	}
}
