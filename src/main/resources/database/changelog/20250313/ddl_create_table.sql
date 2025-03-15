DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS coupon;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_item;

CREATE TABLE coupon
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    coupon_type ENUM ('FIXED', 'RATE') NOT NULL,
    discount    DECIMAL(10, 2)         NOT NULL
);

CREATE TABLE product
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100)   NOT NULL,
    price          DECIMAL(10, 2) NOT NULL,
    stock_quantity INT            NOT NULL,
    coupon_id      INT,
    FOREIGN KEY (coupon_id) REFERENCES coupon (id) ON DELETE SET NULL
);

CREATE TABLE orders
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    total_price DECIMAL(10, 2) NOT NULL,
    order_date  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_item
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    order_id         INT,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    product_id       INT,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    quantity         INT            NOT NULL,
    discount_applied DECIMAL(10, 2) DEFAULT 0.00,
    final_price      DECIMAL(10, 2) NOT NULL
);

INSERT INTO coupon(coupon_type, discount)
VALUES ('FIXED', 20.00);
INSERT INTO coupon(coupon_type, discount)
VALUES ('FIXED', 40.00);
INSERT INTO coupon(coupon_type, discount)
VALUES ('RATE', 15.00);
INSERT INTO coupon(coupon_type, discount)
VALUES ('RATE', 50.00);
INSERT INTO coupon(coupon_type, discount)
VALUES ('RATE', 10.00);

INSERT INTO orders(total_price)
VALUES (0.00);
INSERT INTO orders(total_price)
VALUES (0.00);
