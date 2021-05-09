package com.shopifychal.imagerepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageCrudRepo extends CrudRepository<Image, Long>{}