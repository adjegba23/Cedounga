package com.krouba.Cedounga.upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FileUploadRepository extends JpaRepository<UploadedFile, String> {

}
