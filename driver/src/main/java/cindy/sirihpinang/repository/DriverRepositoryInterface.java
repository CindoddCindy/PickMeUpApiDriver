package cindy.sirihpinang.repository;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cindy.sirihpinang.model.Driver;

public interface DriverRepositoryInterface {

    Long size();
    List<Driver> findAll (int page, int limit);
    Driver findById (@NotNull Long id);
    boolean save(@NotNull Driver driver);
    boolean update(@NotNull Long id, @NotBlank String name,@NotBlank String email, @NotBlank String password, @NotBlank String driverid, @NotBlank String drivercomname); // @NotNull int grade);
    boolean destroy(@NotNull Long id);
}