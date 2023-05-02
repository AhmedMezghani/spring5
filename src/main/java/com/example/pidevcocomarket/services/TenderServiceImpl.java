package com.example.pidevcocomarket.services;

import com.example.pidevcocomarket.entities.Tender;
import com.example.pidevcocomarket.interfaces.ITenderService;
import com.example.pidevcocomarket.repositories.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenderServiceImpl implements ITenderService {
    @Autowired
    private TenderRepository tenderRepository;

    @Override
    public Optional<Tender> getTenderById(int id) {
        return tenderRepository.findById(id);
    }

    @Override
    public List<Tender> getAllTenders() {
        return tenderRepository.findAll();
    }

    @Override
    public Tender addTender(Tender tender) {
        return tenderRepository.save(tender);
    }

    @Override
    public Tender updateTender(Tender tender) {
        return tenderRepository.save(tender);
    }

    @Override
    public void deleteTender(int id) {
        tenderRepository.deleteById(id);
    }
}
