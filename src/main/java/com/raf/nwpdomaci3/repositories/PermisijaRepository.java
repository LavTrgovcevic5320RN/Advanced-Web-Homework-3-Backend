package com.raf.nwpdomaci3.repositories;

import com.raf.nwpdomaci3.model.Permisija;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisijaRepository {
    public List<Permisija> findById(Long id);
}
