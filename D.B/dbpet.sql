-- --------------------------------------------------------
-- Servidor:                     XXX.0.0.X
-- Versão do servidor:           5.5.5-10.4.21-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Copiando estrutura do banco de dados para dbpet
CREATE DATABASE IF NOT EXISTS `dbpet` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `dbpet`;


-- Copiando estrutura para tabela dbpet.animal
CREATE TABLE IF NOT EXISTS `animal` (
  `Tipo` varchar(35) DEFAULT NULL,
  `Id_Ani` int(11) NOT NULL,
  `Especie` varchar(65) DEFAULT NULL,
  `Nome_ani` varchar(35) DEFAULT NULL,
  `Raca` varchar(65) DEFAULT NULL,
  `Genero` varchar(35) DEFAULT NULL,
  `Porte` varchar(35) DEFAULT NULL,
  `Id_Clien` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_Ani`),
  KEY `Id_Clien` (`Id_Clien`),
  CONSTRAINT `animal_ibfk_1` FOREIGN KEY (`Id_Clien`) REFERENCES `cliente` (`Id_Clien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportação de dados foi desmarcado.


-- Copiando estrutura para tabela dbpet.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `Id_Clien` int(11) NOT NULL,
  `Nome` varchar(65) DEFAULT NULL,
  `Endereco` varchar(165) DEFAULT NULL,
  `Telefone` varchar(15) DEFAULT NULL,
  `CPF` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`Id_Clien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportação de dados foi desmarcado.


-- Copiando estrutura para tabela dbpet.faz_compra
CREATE TABLE IF NOT EXISTS `faz_compra` (
  `Id_Comp` int(11) NOT NULL AUTO_INCREMENT,
  `Data_hora` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `Quant_item` varchar(10) DEFAULT NULL,
  `Tipo_pagto` varchar(35) DEFAULT NULL,
  `Valor_total` decimal(10,0) DEFAULT NULL,
  `Id_Clien` int(11) DEFAULT NULL,
  `Id_Prod` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_Comp`),
  KEY `Id_Clien` (`Id_Clien`),
  KEY `Id_Prod` (`Id_Prod`),
  CONSTRAINT `faz_compra_ibfk_1` FOREIGN KEY (`Id_Clien`) REFERENCES `cliente` (`Id_Clien`),
  CONSTRAINT `faz_compra_ibfk_2` FOREIGN KEY (`Id_Prod`) REFERENCES `produtos` (`Id_Prod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportação de dados foi desmarcado.


-- Copiando estrutura para tabela dbpet.os
CREATE TABLE IF NOT EXISTS `os` (
  `Id_OS` int(11) NOT NULL,
  `Data_hora` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `Servico` varchar(65) DEFAULT NULL,
  `Profissional` varchar(35) DEFAULT NULL,
  `Nome_animal` varchar(35) DEFAULT NULL,
  `Cor_Animal` varchar(35) DEFAULT NULL,
  `Valor_total` decimal(10,0) DEFAULT NULL,
  `Tipo_Pagto` varchar(10) DEFAULT NULL,
  `Id_Ani` int(11) DEFAULT NULL,
  `Id_User` int(11) DEFAULT NULL,
  `Id_Clien` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_OS`),
  KEY `Id_Ani` (`Id_Ani`),
  KEY `Id_User` (`Id_User`),
  KEY `Id_Clien` (`Id_Clien`),
  CONSTRAINT `os_ibfk_1` FOREIGN KEY (`Id_Ani`) REFERENCES `animal` (`Id_Ani`),
  CONSTRAINT `os_ibfk_2` FOREIGN KEY (`Id_User`) REFERENCES `usuarios` (`Id_User`),
  CONSTRAINT `os_ibfk_3` FOREIGN KEY (`Id_Clien`) REFERENCES `cliente` (`Id_Clien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportação de dados foi desmarcado.


-- Copiando estrutura para tabela dbpet.produtos
CREATE TABLE IF NOT EXISTS `produtos` (
  `Id_Prod` int(11) NOT NULL,
  `Tipo` varchar(35) DEFAULT NULL,
  `nome_prod` varchar(50) DEFAULT NULL,
  `Valor` varchar(10) DEFAULT NULL,
  `Validade` varchar(15) DEFAULT NULL,
  `Quantidade` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Id_Prod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportação de dados foi desmarcado.


-- Copiando estrutura para tabela dbpet.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `Id_User` int(11) NOT NULL,
  `Nome_USER` varchar(65) DEFAULT NULL,
  `Telefone` varchar(15) DEFAULT NULL,
  `Login` varchar(35) DEFAULT NULL,
  `Senha` varchar(10) DEFAULT NULL,
  `Perfil` varchar(35) DEFAULT NULL,
  PRIMARY KEY (`Id_User`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportação de dados foi desmarcado.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
