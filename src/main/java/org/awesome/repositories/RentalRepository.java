package org.awesome.repositories;

import org.awesome.entities.Rental;
import org.awesome.entities.RentalBook;
import org.awesome.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    public List<Rental> findAllByUser(User user);
    public List<Rental> findAllByBook(RentalBook book);



    default Page<Rental> getRentalList() {
        /** 페이징 처리 S */
        int page = 1;
        int limit = 20;

        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<Rental> data = findAll(pageable);

        return data;
    }


}
