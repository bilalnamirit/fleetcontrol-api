CREATE TABLE maintenances (
    id          BIGSERIAL PRIMARY KEY,
    type         VARCHAR(255) NOT NULL,
    service_date    DATE NOT NULL,
    cost            NUMERIC(10,2) NOT NULL CHECK (cost>=0),
    mileage_at_service INT NOT NULL CHECK (mileage_at_service>=0),
    notes               VARCHAR(500),
    vehicle_id          BIGINT NOT NULL REFERENCES vehicles (id) ON DELETE CASCADE
);

CREATE INDEX idx_maintenances_vehicle_id ON maintenances (vehicle_id);

CREATE TABLE fuel_logs(
    id          BIGSERIAL PRIMARY KEY,
    log_date    DATE NOT NULL,
    liters      NUMERIC(8,2) NOT NULL CHECK (liters>=0),
    total_price NUMERIC(10,2) NOT NULL CHECK(total_price >= 0),
    mileage     INT NOT NULL CHECK(mileage>=0),
    vehicle_id  BIGINT NOT NULL REFERENCES vehicles (id) ON DELETE CASCADE
);

CREATE INDEX idx_fuel_logs_vehicle_id ON fuel_logs (vehicle_id);