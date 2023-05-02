package com.example.pidevcocomarket.interfaces;

import com.example.pidevcocomarket.entities.Tender;

import java.util.List;
import java.util.Optional;

public interface ITenderService {
    Optional<Tender> getTenderById(int id);

    List<Tender> getAllTenders();

    Tender addTender(Tender tender);

    Tender updateTender(Tender tender);

    void deleteTender(int id);
}
