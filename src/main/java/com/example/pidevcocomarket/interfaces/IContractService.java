package com.example.pidevcocomarket.interfaces;

import com.example.pidevcocomarket.entities.Contract;

import java.util.List;

public interface IContractService {
    List<Contract> getContracts();

    Contract getContractById(Long id);

    Contract createContract(Contract contract);

    Contract updateContract(Long id, Contract contract);

    void deleteContract(Long id);
}