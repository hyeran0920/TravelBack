package filminkorea.fik.repositories;

import filminkorea.fik.entities.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThumbnailRepository extends JpaRepository<Thumbnail, Integer> {
}
