/*AGREGAR DATOS*/
USE bdPV;
INSERT INTO tipoEmpleado VALUES ('C','Cajero'),
								('A','Administrador');
INSERT INTO metodoPago VALUES('E','Efectivo'),
								('T','Tarjeta Credito/Debito'),
								('V','Vales despensa'),
								('D','Dolares');
INSERT INTO unidadMedida VALUES('U','Por Unidad/Pieza'),
								('G','A Granel'),
								('P','Paquete kit');
CREATE LOGIN ADMS WITH PASSWORD ='ADMIN'
CREATE USER adms FOR LOGIN ADMS;
GRANT CONNECT TO adms;


/*select* from sucursal
select* from empleados
insert into empleados values ('AD','ADMINIS','GGG','4612548574','20/10/2018','A')
insert into sucursal values ('RAPL980311','tienda','lagos','461352121','celaua','AD')
DELETE sucursal 
DELETE empleados */

CREATE PROCEDURE usuNull  
   @ID INT OUTPUT  
AS  
BEGIN  
   SELECT @ID = count(*) 
   from empleados
END

CREATE PROCEDURE sucuNull  
   @ID INT OUTPUT  
AS  
BEGIN  
   SELECT @ID = count(*) 
   from sucursal
END

GRANT EXECUTE ON sucuNull TO adms
GRANT EXECUTE ON usuNull TO adms
GRANT EXECUTE ON insUsuario TO adms
GRANT EXECUTE ON addUser TO adms
GRANT INSERT,SELECT ON empleados TO adms
GRANT INSERT ON sucursal TO adms


use bdPV;
CREATE ROLE cajero
CREATE ROLE administrador
GRANT DBA to administrador
GRANT ALL PRIVILEGES ON factura TO administrador
GRANT SELECT,UPDATE,DELETE,INSERT,EXECUTE ON EMPLEADOS to administrador
GRANT SELECT,UPDATE,DELETE,INSERT ON tipoEmpleado to administrador
GRANT SELECT,UPDATE,DELETE,INSERT ON clientes to administrador
GRANT SELECT,UPDATE,DELETE,INSERT ON departamento to administrador
GRANT SELECT,UPDATE,DELETE,INSERT ON detalle to administrador
GRANT SELECT,UPDATE,DELETE,INSERT ON envios to administrador
GRANT SELECT,UPDATE,DELETE,INSERT ON factura to administrador
GRANT SELECT,UPDATE,DELETE,INSERT ON metodoPago to administrador
GRANT SELECT,UPDATE,DELETE,INSERT ON pagos to administrador
GRANT SELECT,UPDATE,DELETE,INSERT ON tipoEmpleado to administrador
GRANT ALL PRIVILEGES ON productos TO administrador
GRANT ALL PRIVILEGES ON promociones TO administrador
GRANT ALL PRIVILEGES ON proveedores TO administrador
GRANT ALL PRIVILEGES ON sucursal TO administrador
GRANT ALL PRIVILEGES ON unidadMEdida TO administrador
/*GRANT SELECT,UPDATE,DELETE,INSERT ON DATABASE bdPV to administrador*/
drop PROCEDURE insUsuario
CREATE PROCEDURE insUsuario

@id nvarchar(15) , @no nvarchar(60), @dom nvarchar(100), @tel nvarchar (15), @con nvarchar(40), @fec date, @idT nvarchar(1)
as 

	begin 
		INSERT INTO empleados VALUES(@id,@no,@dom,@tel,@con,@fec,@idT);	
		execute addUser @id
						/*print 'LLEGUE 1'*/
						/*exec sp_addlogin @id,@con,'bdPV',null,null,null
						exec sp_adduser @id,@id,null	
						exec sp_grantlogin @id
						if @idT = 'A'
							begin
								/*print 'LLEGUE 2'*/
								exec sp_addrolemember administrador,@id
							end
						else
							begin		
								exec sp_addrolemember cajero,@id
								CREATE LOGIN STR(@id) WITH PASSWORD = CONVERT(varchar,@con)
							end		*/
	end
	
exec insUsuario 'admin','administrador de la tienda','Lago de guadalupe 226','4611380853','quique','20-10-2018','A'
SELECT idUsu, nombre FROM empleados where idTip='A';
SELECT * FROM sys.schemas;
select* from empleados
DROP USER AD
ALTER AUTHORIZATION ON SCHEMA::AD TO dbo;
Drop schema AD
SELECT*FROM sucursal
INSERT INTO sucursal VALUES (?,?,?,?,?,?);
SELECT idUsu
FROM empleados
WHERE nombre=?;
drop PROCEDURE addUser 
CREATE PROCEDURE addUser 
@idU varchar(15) ,  @idT nvarchar(1)
as
	begin 
	declare @id varchar(15)
	declare @con varchar(40)
	Select @id=idUsu, @con=contrasena
	FROM empleados
	WHERE idUsu=@idU
			exec sys.sp_addlogin @id,@con,'bdPV',null,null,null
			exec sys.sp_adduser @id,@id,null	
			exec sys.sp_grantlogin @id
			if @idT = 'A'
							begin
								
								exec sys.sp_addrolemember administrador,@id
							end
						else
							begin		
								exec sys.sp_addrolemember cajero,@id
								
							end	
end


create procedure Procedure_pn_programacion
@fechainiprog varchar(100) 
as
	declare @sqlselect nvarchar(500)
	set @sqlselect = '
	declare @fecha datetime 
	Set @fecha = convert(datetime,' + @fechainiprog + ', 102)'
	
	exec sp_executesql @sqlselect
go

exec Procedure_pn_programacion '01-01-2013 ';
go

/*      SOLUCIÓN   */
CREATE PROCEDURE 