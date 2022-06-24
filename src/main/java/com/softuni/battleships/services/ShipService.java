package com.softuni.battleships.services;

import com.softuni.battleships.DTO.CreateShipDTO;
import com.softuni.battleships.DTO.ShipDTO;
import com.softuni.battleships.models.Category;
import com.softuni.battleships.models.Ship;
import com.softuni.battleships.models.ShipType;
import com.softuni.battleships.models.User;
import com.softuni.battleships.repositories.CategoryRepository;
import com.softuni.battleships.repositories.ShipRepository;
import com.softuni.battleships.repositories.UserRepository;
import com.softuni.battleships.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LoggedUser loggedUser;

    public ShipService(ShipRepository shipRepository, CategoryRepository categoryRepository, UserRepository userRepository, LoggedUser loggedUser) {
        this.shipRepository = shipRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
    }

    public boolean create(CreateShipDTO createShipDTO) {
        Optional<Ship> byName = this.shipRepository.findByName(createShipDTO.getName());

        if (byName.isPresent()) {
            return false;
        }

        ShipType type = switch (createShipDTO.getCategory()) {
            case 0 -> ShipType.BATTLE;
            case 1 -> ShipType.CARGO;
            case 2 -> ShipType.PATROL;
            default -> ShipType.BATTLE;
        };

        Category category = this.categoryRepository.findByName(type);
        Optional<User> owner = this.userRepository.findById(this.loggedUser.getId());

        Ship ship = new Ship();
        ship.setName(createShipDTO.getName());
        ship.setPower(createShipDTO.getPower());
        ship.setHealth(createShipDTO.getHealth());
        ship.setCreated(createShipDTO.getCreated());
        ship.setCategory(category);
        ship.setUser(owner.get());

        this.shipRepository.save(ship);

        return true;
    }

    public List<ShipDTO> getShipsOwnedBy(long ownerId) {
        return this.shipRepository.findByUserId(ownerId)
                .stream().map(ShipDTO::new).collect(Collectors.toList());
    }

    public List<ShipDTO> getShipsNotOwnedBy(long enemyId) {
        return null;
    }
}
