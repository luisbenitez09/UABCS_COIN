DROP DATABASE IF EXISTS UABCS_COIN;

CREATE DATABASE UABCS_COIN;

USE UABCS_COIN;

/* CREAR TABLAS */

CREATE TABLE users (
	userId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	fisrt_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	email VARCHAR(40) NOT NULL UNIQUE,
	pass CHAR(64) NOT NULL,
	cumple DATE NOT NULL );

CREATE TABLE tiposCuenta (
	tipoId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	nombreTipo VARCHAR (20) NOT NULL,
	saldoMaximo FLOAT,
	maxTransfer FLOAT NOT NULL );

CREATE TABLE cuentas (
	cuentaId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	userCuenta INT NOT NULL,
	tipoCuenta INT NOT NULL,
	saldo FLOAT NOT NULL,
	CONSTRAINT fk_tipo FOREIGN KEY (tipoCuenta) REFERENCES tiposCuenta (tipoId) ON DELETE CASCADE,
	CONSTRAINT fk_user FOREIGN KEY (userCuenta) REFERENCES users (userId) ON DELETE CASCADE );

ALTER TABLE cuentas AUTO_INCREMENT = 3762;

CREATE TABLE transacciones (
	cuentaTransferencia INT NOT NULL,
	cuentaDeposito INT NOT NULL,
	saldoTransferencia FLOAT NOT NULL,
	fechaTranfer DATETIME DEFAULT NOW() NOT NULL,
	CONSTRAINT fk_cDeposito FOREIGN KEY (cuentaDeposito) REFERENCES cuentas (cuentaId) ON DELETE CASCADE,
	CONSTRAINT fk_cTransfer FOREIGN KEY (cuentaTransferencia) REFERENCES cuentas (cuentaId) ON DELETE CASCADE );

CREATE TABLE cuentasFrecuentes (
	cuentaRecurrente INT NOT NULL,
	cuentaMaster INT NOT NULL,
	CONSTRAINT fk_cRecurrente FOREIGN KEY (cuentaRecurrente) REFERENCES cuentas (cuentaId) ON DELETE CASCADE,
	CONSTRAINT fk_cMaster FOREIGN KEY (cuentaMaster) REFERENCES cuentas (cuentaId) ON DELETE CASCADE );


/* ------------------ Modelo usuario -------------------*/


DROP PROCEDURE IF EXISTS ADD_USER;
DELIMITER $$
CREATE PROCEDURE ADD_USER(
IN nombre VARCHAR(40),
IN apellido VARCHAR(40),
IN email VARCHAR(40),
IN contra VARCHAR(40),
IN fecha_nacimiento VARCHAR(40),
OUT code INT)
BEGIN
	

	START TRANSACTION;
	INSERT INTO users(fisrt_name, last_name, email, pass, cumple) VALUES (nombre, apellido, email, SHA1(contra), fecha_nacimiento);
	SET code = 1;
	IF contra NOT REGEXP '(?=.*[a-z])(?=.*[0-9])(?=.*[?$/])(?=.{8,})' THEN
		BEGIN
		  SET code = -1; /* La contraseña debe tener al menos 8 dígitos, un número, y un símbolo especial.*/
		  ROLLBACK;
		END;
	END IF;

	IF NOT email REGEXP '^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+([.]([a-zA-Z]{2,5})){1,2}$' THEN
		BEGIN
			SET code = -2; /*El email no es valido */
			ROLLBACK;
 	END;

END IF;
	
	IF (TRUNCATE(DATEDIFF(CURRENT_DATE(), fecha_nacimiento)/365,0) < 18) THEN
		BEGIN
			SET code = -3; /*Debes ser mayor de edad para registrarte en UBCS COIN*/
			ROLLBACK;
		END;
	END IF;
	
	COMMIT;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS FIND_BY_LOGIN;
DELIMITER $$ 
CREATE PROCEDURE FIND_BY_LOGIN(
IN correo VARCHAR(40),
IN contra VARCHAR(40),
OUT nombre VARCHAR(40),
OUT apellido VARCHAR (40),
OUT id INT,
OUT verificación BOOLEAN, 
OUT mail VARCHAR(40))
BEGIN
	DECLARE aux VARCHAR(40);
	SELECT fisrt_name INTO aux FROM users WHERE email = correo AND pass = sha1(contra);

	IF aux IS NOT NULL THEN
		BEGIN
			SET verificación = true;
			SET nombre = aux;
			SELECT last_name INTO apellido FROM users WHERE email = correo AND pass = sha1(contra);
			SELECT userId INTO id FROM users WHERE email = correo AND pass = sha1(contra);
			SELECT email INTO mail FROM users WHERE email = correo AND pass = sha1(contra);
		END;
	END IF;
END $$
DELIMITER ;


/*-------------------Modelo Cuenta -------------------*/

DROP PROCEDURE IF EXISTS ADD_CUENTA;
DELIMITER $$
CREATE PROCEDURE ADD_CUENTA(
IN usuario INT,
IN tipo INT,
IN money INT,
OUT code INT)
BEGIN
	DECLARE saldoM INT;

	START TRANSACTION;
		SELECT saldoMaximo INTO saldoM FROM tiposCuenta WHERE tipoId = tipo;

		IF saldoM > 0 THEN
			BEGIN
				IF money <= saldoM THEN
					BEGIN
						INSERT INTO cuentas(userCuenta, tipoCuenta, saldo) VALUES (usuario, tipo, money);
						SET code = 1;
						COMMIT;
					END;
				ELSE
					SET code = -1; /* Estas exediendo el limite máximo de saldo permitido en esta cuenta*/
					ROLLBACK;
				END IF;
			END;
		ELSE
			BEGIN
				INSERT INTO cuentas(userCuenta, tipoCuenta, saldo) VALUES (usuario, tipo, money);
				SET code = 1;
				COMMIT;
			END;
		END IF;
END $$
DELIMITER ;



DROP PROCEDURE IF EXISTS ADD_FRECUENTE;
DELIMITER $$
CREATE PROCEDURE ADD_FRECUENTE(
IN cuenta INT,
IN cuentaM INT,
OUT status INT)
BEGIN
	
	SELECT cuentaRecurrente INTO @aux FROM cuentasFrecuentes WHERE cuentaRecurrente = cuenta AND cuentaMaster = cuentaM;

	IF @aux IS NOT NULL THEN
			SET status = -1; /* La cuenta ya esta en tu lista de Cuentas Frecuentes.*/
	ELSE 
		INSERT INTO cuentasFrecuentes VALUES (cuentaM, cuenta);
		SET status = 1;
	END IF;
END $$
DELIMITER ;



DROP PROCEDURE IF EXISTS DINERO_CUENTA;
DELIMITER $$
CREATE PROCEDURE DINERO_CUENTA(
IN cuenta INT,
OUT saldo_cuenta INT)
BEGIN
	SELECT saldo into saldo_cuenta FROM cuentas WHERE cuentaId = cuenta;
END $$
DELIMITER ;



DROP PROCEDURE IF EXISTS IMG_CARD;
DELIMITER $$
CREATE PROCEDURE IMG_CARD(
IN cuenta INT,
OUT tipo INT)
BEGIN
	SELECT tipoCuenta INTO tipo FROM cuentas WHERE cuentaId = cuenta;
END$$
DELIMITER ;



DROP PROCEDURE IF EXISTS INICIO_CUENTA;
DELIMITER $$
CREATE PROCEDURE INICIO_CUENTA(
IN id INT,
OUT normal INT,
OUT gold INT,
OUT premium INT)
BEGIN
	SELECT SUM(cuentas.saldo) INTO normal FROM cuentas WHERE userCuenta = id AND tipoCuenta = 1;
	SELECT SUM(cuentas.saldo) INTO gold FROM cuentas WHERE userCuenta = id AND tipoCuenta = 2;
	SELECT SUM(cuentas.saldo) INTO premium FROM cuentas WHERE userCuenta = id AND tipoCuenta = 3;
END $$
DELIMITER ;



DROP PROCEDURE IF EXISTS TRANSFERENCIA;
DELIMITER $$
CREATE PROCEDURE TRANSFERENCIA(
IN cuentaOrigen INT,
IN cuentaDestino INT,
IN saldoT FLOAT,
OUT code INT)
BEGIN
	
	
	DECLARE fondos FLOAT;  /* Guarda la cantidad de dinero que tiene el usuario que manda el dinero*/
	DECLARE cantidad_permitida FLOAT; /* Revisa si puedes transferir la cantidad de dinero  */
	DECLARE limite_cuenta FLOAT; /* Revisa cuanto es lo maximo que puede tener en la cuenta. */

	SELECT cuentas.saldo INTO fondos FROM cuentas WHERE cuentaOrigen = cuentaId;
	SELECT saldoMaximo INTO limite_cuenta FROM tiposCuenta JOIN cuentas ON tipoCuenta = tipoId WHERE cuentaId = cuentaDestino;
	SELECT maxTransfer INTO cantidad_permitida FROM tiposCuenta JOIN cuentas ON tipoCuenta = tipoId WHERE cuentaId = cuentaOrigen;


	START TRANSACTION;
	BEGIN
		/* Modificar para que sume solo las transacciones del dia actual */
		SELECT SUM(saldoTransferencia) INTO @totalTransferencia FROM transacciones WHERE cuentaTransferencia = cuentaOrigen;
		SET @totalTransferencia = @totalTransferencia + saldoT;
			
		SELECT cuentas.saldo INTO @money FROM cuentas WHERE cuentaId = cuentaDestino;
		SET @totalDinero = saldoT + @money;

		IF (cuentaOrigen like cuentaDestino) THEN /* Valida que no se transfiera a si mismo*/ 
			BEGIN
				SET code = -1; /*No puedes hacer un deposito a la misma cuenta.*/
				ROLLBACK;
			END;

		ELSEIF fondos < saldoT THEN               /* Valida que le alcanze el dinero para transferir*/
			BEGIN
				SET code = -2; /*Tú saldo es insuficiente, por favor, intente con otra cantidad.*/
				ROLLBACK;
			END;

		ELSEIF cantidad_permitida NOT LIKE NULL THEN              /* Valida que si es null, no tiene limite de cuenta*/
			IF @totalTrasferencia > cantidad_permitida THEN                                                             /* Hacer Ajuste*/
				BEGIN
					SET code = -3; /*No puedes exeder tu limite de transferencia diaria*/
					ROLLBACK;
				END;
			END IF;

		ELSEIF limite_cuenta < @totalDinero THEN
			BEGIN
				SET code = -4; /*No puedes exceder el limite de tu cuenta*/
				ROLLBACK;
			END;
		ELSE
			UPDATE cuentas SET saldo = cuentas.saldo - saldoT WHERE cuentaId = cuentaOrigen;			
			UPDATE cuentas SET saldo = @totalDinero WHERE cuentaId = cuentaDestino;

			INSERT INTO transacciones(cuentaTransferencia,cuentaDeposito,saldoTransferencia) VALUES (cuentaOrigen, cuentaDestino, saldoT);
			SET code = 1;
			COMMIT;
		END IF;
	END;
END $$
DELIMITER ;



DROP PROCEDURE IF EXISTS ULTIMO_MOV;
DELIMITER $$
CREATE PROCEDURE ULTIMO_MOV(
IN cuenta INT,
OUT fecha VARCHAR(20))
BEGIN
	SELECT fechaTranfer INTO fecha FROM transacciones WHERE cuentaTransferencia = cuenta or cuentaDeposito = cuenta ORDER by fechaTranfer DESC LIMIT 1;
END $$
DELIMITER ;



DROP PROCEDURE IF EXISTS VALIDAR_CUENTA;
DELIMITER $$
CREATE PROCEDURE VALIDAR_CUENTA(
IN cuentaDestino INT,
OUT valida BOOLEAN)
BEGIN
	DECLARE aux VARCHAR(40);
	SELECT cuentaId INTO aux FROM cuentas WHERE cuentaId = cuentaDestino;

	IF aux IS NOT NULL THEN
		SET valida = true;
	END IF;
END $$
DELIMITER ;

INSERT INTO tiposCuenta(nombreTipo, saldoMaximo, maxTransfer) VALUES("Básica", 10000, 5000);
INSERT INTO tiposCuenta(nombreTipo, saldoMaximo, maxTransfer) VALUES("Gold", 20000, 1000);
INSERT INTO tiposCuenta(nombreTipo, saldoMaximo, maxTransfer) VALUES("Básica", 0, 50000);