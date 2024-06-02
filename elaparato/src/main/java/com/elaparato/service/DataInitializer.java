package com.elaparato.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;


@Component
public class DataInitializer implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen datos en las tablas
        Long countProducts = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Producto", Long.class);
        Long countVentas = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Venta", Long.class);

        if (countProducts == 0 && countVentas == 0) {
            // Insertar productos
            jdbcTemplate.batchUpdate(
                    "INSERT INTO Producto (id, nombre, descripcion, precio, cantidad) VALUES (?, ?, ?, ?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            Object[][] products = {
                                    {1, "Lavadora Samsung", "Lavadora de carga frontal con capacidad de 15 kg", 1299.99, 10},
                                    {2, "Refrigeradora LG", "Refrigeradora de 3 puertas con capacidad de 25 pies cúbicos", 1599.99, 5},
                                    {3, "Televisor Sony", "Televisor LED 4K de 55 pulgadas con Android TV", 899.99, 15},
                                    {4, "Horno eléctrico Whirlpool", "Horno eléctrico de acero inoxidable con capacidad de 6.4 pies cúbicos", 799.99, 7},
                                    {5, "Aspiradora Dyson", "Aspiradora inalámbrica con tecnología Cyclone V10", 499.99, 20}
                            };
                            ps.setInt(1, (Integer) products[i][0]);
                            ps.setString(2, (String) products[i][1]);
                            ps.setString(3, (String) products[i][2]);
                            ps.setDouble(4, (Double) products[i][3]);
                            ps.setInt(5, (Integer) products[i][4]);
                        }

                        @Override
                        public int getBatchSize() {
                            return 5;
                        }
                    }
            );

            // Insertar ventas
            jdbcTemplate.batchUpdate(
                    "INSERT INTO Venta (id_venta, fecha) VALUES (?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            Object[][] ventas = {
                                    {1, "2023-01-15"},
                                    {2, "2023-02-05"},
                                    {3, "2023-03-10"},
                                    {4, "2023-04-20"},
                                    {5, "2023-05-01"}
                            };
                            ps.setInt(1, (Integer) ventas[i][0]);
                            ps.setString(2, (String) ventas[i][1]);
                        }

                        @Override
                        public int getBatchSize() {
                            return 5;
                        }
                    }
            );

            // Insertar los productos comprados en cada venta
            jdbcTemplate.batchUpdate(
                    "INSERT INTO producto_lista_ventas (lista_productos_id, lista_ventas_id_venta) VALUES (?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            Object[][] productosVentas = {
                                    {1, 1},
                                    {2, 1},
                                    {3, 1},
                                    {1, 2},
                                    {4, 2},
                                    {2, 3},
                                    {5, 3},
                                    {3, 3},
                                    {1, 4},
                                    {2, 4},
                                    {5, 4},
                                    {3, 5},
                                    {4, 5}
                            };
                            ps.setInt(1, (Integer) productosVentas[i][0]);
                            ps.setInt(2, (Integer) productosVentas[i][1]);
                        }

                        @Override
                        public int getBatchSize() {
                            return 13;
                        }
                    }
            );

            // Modificar las secuencias (ajustar AUTO_INCREMENT)
            jdbcTemplate.execute("ALTER TABLE Producto AUTO_INCREMENT = 6");
            jdbcTemplate.execute("ALTER TABLE Venta AUTO_INCREMENT = 6");
        }
    }
}
