package com.backend.EventManagementSystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.lang.Integer;
import com.backend.EventManagementSystem.entities.Event;
@Repository
public interface EventRepository extends MongoRepository<Event,String>{

}
