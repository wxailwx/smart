package com.smart.smart.service;

import com.smart.smart.model.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Integer> {
        File findById(int id);
        Page<File> findAll(Pageable pageable);
        Page<File> findByFileNameContaining(String fileName,Pageable pageable);
}
