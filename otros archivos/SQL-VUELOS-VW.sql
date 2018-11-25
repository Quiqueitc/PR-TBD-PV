USE VUELOS;
/*1. Crear una vista que permita obtener el número de vuelo, nombre de la línea aérea y
horario de los vuelos programados el día lunes al aeropuerto de Los Ángeles. */
SELECT* FROM ProgramaVuelos;
SELECT* FROM Aeropuerto
select*from diasV;
select*from dias;

CREATE VIEW vw_1 AS
SELECT P.noVuelo,L.nombre, P.horario
FROM ProgramaVuelos P INNER JOIN LineaAerea L ON P.CveLinea=L.CveLinea INNER JOIN diasV D ON P.noVuelo=D.noVuelo
WHERE D.dia=(SELECT dia FROM Dias WHERE nombre='Lunes') and P.CveAeropuertoDestino=(SELECT CveAeropuerto FROM Aeropuerto WHERE nombre='loa angeles')
/* 2. Crear una vista que muestre los nombres de las líneas aéreas que no tienen vuelos a la
ciudad de México D.F. */
SELECT* FROM Poblacion
SELECT* FROM Aeropuerto;
CREATE VIEW vw_2 AS 
SELECT LA.nombre 
FROM LineaAerea LA 
WHERE LA.nombre NOT IN 
(SELECT L.nombre 
FROM LineaAerea L INNER JOIN ProgramaVuelos P 
		ON L.CveLinea=P.CveLinea INNER JOIN Aeropuerto A 
			on A.CveAeropuerto=P.CveAeropuertoDestino INNER JOIN Poblacion PO
				 ON A.CvePais=PO.CvePais and A.CveEstado=PO.CveEstado 
				 and A.CvePoblacion=PO.CvePoblacion
WHERE PO.nombre='MEXICO');
										
/*3. Crear una vista que me permita actualizar las reservaciones de 
los vuelos de Mexicana*/
SELECT * FROM Aviones

CREATE VIEW vw_11 (noAvion,nombre,fechaUl,linea,modelo)
AS SELECT *
FROM Aviones
WITH CHECK OPTION 

INSERT INTO vw_11
values ('AAB033','Aguila 3','18/09/2018','AA',1);

/*vista sobre tabla asientos*/
SELECT* FROM Aviones
CREATE VIEW vw_3 AS
SELECT *
	FROM Asientos
	WHERE noVuelo in (SELECT noVuelo
						FROM ProgramaVuelos
						WHERE CveLinea in (SELECT CveLinea
											FROM LineaAerea
											WHERE nombre='Mexicana'))
WITH CHECK OPTION
/*4. Crear una vista que muestre el aeropuerto de origen, los de escalas y 
el de destino de un vuelo.*/

drop view vw_4
CREATE VIEW vw_4 (noVuelo,tipo,nombre)
AS 
	SELECT p.noVuelo,'Origen ', a.nombre
	FROM ProgramaVuelos p inner join Aeropuerto a
		on p.CveAeropuertoOrigen=a.CveAeropuerto
	UNION 
	SELECT e.noVuelo, 'Escala' +str(NoEscala), a.nombre
	from Escalas e inner join Aeropuerto a
		ON e.CveAeropuerto=a.CveAeropuerto
	UNION
	SELECT p.noVuelo,'Destino', a.nombre
	FROM ProgramaVuelos p inner join Aeropuerto a
		on p.CveAeropuertoDestino=a.CveAeropuerto
SELECT* 
FROM vw_4 WHERE noVuelo=105


/*5. crear una vista que muestre los nombres de los clientes y el número de vuelo, fecha y
número de asiento para los clientes que tienen reservaciones para los vuelos de
Aeroméxico.*/
select* from asientos;
SELECT* FROM EstadoReserva
SELECT* FROM LineaAerea
CREATE VIEW vw_5 AS 
SELECT C.nombre,A.noVuelo,A.Fecha,noAsiento
FROM Clientes C INNER JOIN Asientos A 
ON C.idCliente=A.idCliente INNER JOIN Vuelos V 
ON A.noVuelo=V.noVuelo and A.Fecha=V.Fecha INNER JOIN ProgramaVuelos P 
ON V.noVuelo=P.noVuelo INNER JOIN LineaAerea L ON P.CveLinea=L.CveLinea
WHERE A.estadoReserva=(SELECT estadoReserva 
						FROM EstadoReserva 
						WHERE descripcion='Reservado') AND L.CveLinea=(SELECT CveLinea FROM LineaAerea WHERE nombre='Aeromexico');
/*6. Crear una vista que muestre la fecha, horario, aeropuerto de origen y de destino de los
vuelos de un cliente determinado. */
CREATE VIEW vw_6 AS
SELECT V.Fecha,PV.horario,A.nombre AS Origen,AD.nombre AS Destino 
FROM Vuelos V INNER JOIN  ProgramaVuelos PV on PV.noVuelo=V.noVuelo INNER JOIN Aeropuerto A on A.CveAeropuerto=PV.CveAeropuertoOrigen INNER JOIN Aeropuerto AD ON PV.CveAeropuertoDestino=AD.CveAeropuerto
INNER JOIN Asientos Asi on V.noVuelo=Asi.noVuelo AND  V.Fecha=Asi.Fecha where Asi.idCliente=(Select idCliente FROM Clientes where idCliente='C1' AND nombre='J. Ramos');
/*7. Crear una vista que muestre las ventas totales por línea aérea. */
SELECT* FROM LineaAerea;
SELECT* FROM VentasDia;
CREATE VIEW vw_7 AS 
SELECT  L.nombre, sum(V.total) AS TotalXDìa
FROM LineaAerea L INNER JOIN VentasDia V on L.CveLinea=V.CveLinea
GROUP BY L.nombre
/*8. crear una vista que muestre los nombres de las líneas aéreas que usan aviones modelo
“A380”. */
select* from ModeloAvion
select* from LineaAerea
select* from ProgramaVuelos
CREATE VIEW vw_8 AS /*inter*/
SELECT L.nombre
FROM LineaAerea L INNER JOIN ProgramaVuelos P ON L.CveLinea=P.CveLinea INNER JOIN ModeloAvion M ON P.modeloAvion=M.modeloAvion
WHERE M.nombre='A380'
/*Select nombre from LineaAerea where CveLinea=(Select CveLinea from ProgramaVuelos PV Inner Join ModeloAvion MA on MA.modeloAvion=PV.modeloAvion where MA.modeloAvion=3);*/
/*9. Crear una vista que muestre el nombre del cliente, y estado de la reservación de los
lugares reservados o vendidos de un vuelo determinado. */
SELECT* FROM ProgramaVuelos
CREATE VIEW vw_9 AS
SELECT C.nombre,E.descripcion
FROM Clientes C INNER JOIN Asientos A ON C.idCliente=A.idCliente INNER JOIN EstadoReserva E on A.estadoReserva=E.estadoReserva INNER JOIN Vuelos V ON A.noVuelo=V.noVuelo and A.Fecha=V.Fecha
WHERE V.noVuelo='101';
/*10. Crear una vista para obtener la fecha, horario y línea aérea de los vuelos de ’nueva
york’ a ‘México’*/
CREATE VIEW vw_10 AS
SELECT V.Fecha,PV.horario,LA.nombre AS Linea_Aerea 
FROM Vuelos V INNER JOIN  ProgramaVuelos PV on PV.noVuelo=V.noVuelo INNER JOIN LineaAerea LA on PV.CveLinea=LA.CveLinea 
where PV.CveAeropuertoOrigen=(Select CveAeropuerto FROM Aeropuerto A
				INNER JOIN Poblacion P on P.CvePais=A.CvePais AND A.CveEstado=P.CveEstado AND P.CvePoblacion=A.CvePoblacion 
				WHERE P.nombre='nueva york')
AND PV.CveAeropuertoDestino=(Select CveAeropuerto FROM Aeropuerto A
				INNER JOIN Poblacion P on P.CvePais=A.CvePais AND A.CveEstado=P.CveEstado AND P.CvePoblacion=A.CvePoblacion 
				WHERE P.nombre='México');
/*11. Cambiar el aeropuerto de salida (cveAeropuertoOrigen) del vuelo 102 de ‘Aeroméxico’
al aeropuerto de la ciudad de puebla. */
CREATE VIEW vw_11 AS

/*12. Actualizar los lugaresdisponibles, lugaresvendidos y lugaresreservados de los vuelos
del día '05/01/2015' con el contenido de la tabla de Reservaciones.,*/
select* FROM vuelos;
UPDATE vuelos SET
	lugaresDisponibles =(SELECT COUNT(*)
						FROM Asientos a
						WHERE a.estadoReserva=(Select estadoReserva FROM EstadoReserva WHERE descripcion='Disponible')
						AND a.Fecha=Vuelos.Fecha
						AND vuelos.noVuelo=a.noVuelo),
	lugaresReservados =(SELECT COUNT(*)
						FROM Asientos a
						WHERE a.estadoReserva=(Select estadoReserva FROM EstadoReserva WHERE descripcion='Reservado')
						AND a.Fecha=Vuelos.Fecha
						AND vuelos.noVuelo=a.noVuelo),
	lugaresVendidos =(SELECT COUNT(*)
						FROM Asientos a
						WHERE a.estadoReserva=(Select estadoReserva FROM EstadoReserva WHERE descripcion='Vendido')
						AND a.Fecha=Vuelos.Fecha
						AND vuelos.noVuelo=a.noVuelo)	
WHERE Fecha='05/01/2015'
/*UPDATE Vuelos set lugaresdisponibles= (SELECT COUNT(*) FROM Asientos A INNER JOIN Vuelos V on A.Fecha=V.Fecha AND A.noVuelo=V.noVuelo
					WHERE estadoReserva=(Select estadoReserva FROM EstadoReserva WHERE descripcion='Disponible')),
		lugaresvendidos=(SELECT COUNT(*) FROM Asientos A INNER JOIN Vuelos V on A.Fecha=V.Fecha AND A.noVuelo=V.noVuelo
					WHERE estadoReserva=(Select estadoReserva FROM EstadoReserva WHERE descripcion='Vendido')),
		lugaresreservados=(SELECT COUNT(*) FROM Asientos A INNER JOIN Vuelos V on A.Fecha=V.Fecha AND A.noVuelo=V.noVuelo
					WHERE estadoReserva=(Select estadoReserva FROM EstadoReserva WHERE descripcion='Reservado'))
where Fecha='05/01/2015';*/
/*13. Actualizar las ventas del día '05/01/2015' de todas las líneas aéreas*/
SELECT* FROM VentasDia
SELECT* FROM VentasDia


UPDATE VentasDia
	SET total=(SELECT SUM(P.precioBoleto*V.lugaresVendidos)
				FROM Vuelos V INNER JOIN ProgramaVuelos P
				ON V.noVuelo=P.noVuelo
				WHERE VentasDia.fecha=V.Fecha
				AND VentasDia.CveLinea=p.CveLinea)
	WHERE fecha='05/01/2015'
update VentasDia
SET iva=total*0.16,
SubTotal=total*0.84
WHERE fecha='05/01/2015'
	
/*14. Crear una vista que permita consultar y actualizar los mantenimientos de los aviones
de Aeroméxico.*/
CREATE VIEW vw_14 AS
SELECT* 
FROM Mantenimiento
	WHERE noAvion IN (SELECT noAvion
		FROM Aviones
		WHERE CveLinea IN (SELECT CveLinea
							FROM LineaAerea
								WHERE nombre='Aeromexico'))
WITH CHECK OPTION 