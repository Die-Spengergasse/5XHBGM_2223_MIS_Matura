package at.spengergasse.backend.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import at.spengergasse.backend.models.Medication;

public interface MedicationRepository extends CrudRepository<Medication, UUID>{
    
}
