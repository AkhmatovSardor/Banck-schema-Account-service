package com.company.Bankaccountservice.repository;

import com.company.Bankaccountservice.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Optional<Account> findByAccountIdAndDeletedAtIsNull(Integer id);

    Set<Account> findAllByTransactionIdAndDeletedAtIsNull(Integer id);
    Set<Account> findAllByLoanIdAndDeletedAtIsNull(Integer id);
    Set<Account> findAllByBranchIdAndDeletedAtIsNull(Integer id);

    @Query(value = "select a from Account a where " +
            "coalesce(:accountId,a.accountId)=a.accountId and " +
            "coalesce(:balance,a.balance)=a.balance and " +
            "coalesce(:type,a.type)=a.type and " +
            "coalesce(:branchId,a.branchId)=a.branchId")
    Page<Account> findByAccountsByPaginationSearch(
            @Param(value = "accountId") Integer accountId,
            @Param(value = "balance") Double balance,
            @Param(value = "type") String type,
            @Param(value = "branchId") Integer branchId,
            Pageable pageable
    );
}
