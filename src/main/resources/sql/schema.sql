CREATE TABLE IF NOT EXISTS `spring`.`token` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `identifier` VARCHAR(50) NOT NULL,
    `token` TEXT NULL,
    PRIMARY KEY(`id`)
);