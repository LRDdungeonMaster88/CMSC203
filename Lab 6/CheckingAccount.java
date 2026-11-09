/** A checking account: a bank account that charges a fee for clearing each check */
public class CheckingAccount extends BankAccount
{
	/** cost of clearing one check (15 cents) */
	public static final double FEE = 0.15;

	/** standard constructor
	@param name the owner of the account
	@param amount the beginning balance */
	public CheckingAccount(String name, double amount)
	{
		super(name, amount);
		// all checking accounts are identified by the extension -10
		setAccountNumber(getAccountNumber() + "-10");
	}

	/** overrides withdraw: the check clearing fee is added to the amount
	@param amount the amount to withdraw (not including the fee)
	@return true if there were sufficient funds, false otherwise */
	@Override
	public boolean withdraw(double amount)
	{
		return super.withdraw(amount + FEE);
	}
}
