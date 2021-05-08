class
	BANK_ACCOUNT

create
	make

feature -- Initialization

	make
			-- Initialize empty account.
		do
			-- Add here your code
		end

feature -- Attributes
	balance : INTEGER
			-- `balance' stores the amount of money in the account

	credit_limit : INTEGER
			-- `credit_limit' represents the limit of the credit of this account

feature -- Queries
	available_amount : INTEGER
			-- `available_amount' is the money available in this account
		do
			Result := balance + credit_limit
		ensure
			Result >= 0
		end

feature -- Commands
	set_credit_limit (amount: INTEGER)
			-- `set_credit_limit' sets the credit limit
		require
			amount_ok: balance + amount >= 0
		do
			credit_limit := amount
		end

	deposit (amount: INTEGER)
			-- `deposit' deposits `amount' to the account
		require
			amount >= 0
		do
			balance := balance + amount
		ensure
			balance = old balance + amount
		end

	withdraw (amount: INTEGER)
			-- `withdraw' withdraws `amount' from the account
		require
			amount >= 0
			amount <= available_amount
		do
			balance := balance - amount
		ensure
			balance = old balance - amount
		end

	transfer (amount: INTEGER; other_account: BANK_ACCOUNT)
			-- `transfer' transfers a `amount' of money to another `other_account'
		require
			other_account /= void
			amount <= available_amount
			amount >= 0
			other_account /= Current
		do
			other_account.deposit(amount)
			withdraw(amount)
		ensure
			balance = old balance - amount
		end

invariant
	credit_positive: credit_limit >= 0
	available_money_non_negative: available_amount >= 0

end
