-- Enable the UUID extension if using PostgreSQL
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Insert categories
INSERT INTO category (id, name, description) VALUES
                                                 (uuid_generate_v4(), 'Electronics', 'Devices and gadgets'),
                                                 (uuid_generate_v4(), 'Books', 'Various genres of books'),
                                                 (uuid_generate_v4(), 'Clothing', 'Apparel and accessories'),
                                                 (uuid_generate_v4(), 'Home & Kitchen', 'Home appliances and kitchenware'),
                                                 (uuid_generate_v4(), 'Sports', 'Sports equipment and apparel'),
                                                 (uuid_generate_v4(), 'Toys', 'Toys and games for children'),
                                                 (uuid_generate_v4(), 'Beauty', 'Beauty and personal care products'),
                                                 (uuid_generate_v4(), 'Automotive', 'Automotive parts and accessories'),
                                                 (uuid_generate_v4(), 'Health', 'Health and wellness products'),
                                                 (uuid_generate_v4(), 'Grocery', 'Food and grocery items');

-- Insert products
DO $$
    DECLARE
        i INT;
        category_id UUID;
    BEGIN
        FOR i IN 1..1000 LOOP
                -- Randomly select a category ID from the existing categories
                SELECT id INTO category_id
                FROM category
                ORDER BY RANDOM()
                LIMIT 1;

                INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES
                    (uuid_generate_v4(),
                     'Product ' || i,
                     'Description for product ' || i,
                     FLOOR(RANDOM() * 100) + 1,  -- Random available quantity between 1 and 100
                     ROUND(CAST(RANDOM() * 100 AS numeric), 2),  -- Random price between 0 and 100, rounded to 2 decimal places
                     category_id);
            END LOOP;
    END $$;
