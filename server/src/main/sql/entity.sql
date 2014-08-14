SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema sbb_schema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sbb_schema` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
-- -----------------------------------------------------
-- Schema new_schema1
-- -----------------------------------------------------
USE `sbb_schema` ;

-- -----------------------------------------------------
-- Table `sbb_schema`.`passenger`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sbb_schema`.`passenger` (
  `passenger_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `birthday` DATE NULL,
  PRIMARY KEY (`passenger_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sbb_schema`.`train`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sbb_schema`.`train` (
  `train_id` INT NOT NULL,
  `number` INT NULL,
  `capacity` INT NULL,
  PRIMARY KEY (`train_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sbb_schema`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sbb_schema`.`ticket` (
  `ticket_id` INT NOT NULL,
  `passenger_id` INT NOT NULL,
  `train_id` INT NOT NULL,
  `date` DATE NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sbb_schema`.`station`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sbb_schema`.`station` (
  `station_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`station_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sbb_schema`.`schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sbb_schema`.`schedule` (
  `schedule_id` INT NOT NULL,
  `station_id` INT NOT NULL,
  `train_id` INT NOT NULL,
  `time` TIME NULL,
  `order` INT NULL)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
