package playground.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleEntityRepo extends MongoRepository<SimpleEntity, String> {

}
