INSERT INTO coffee (id, name, create_date, region)
VALUES ('9ba5f1b2-fc3c-42aa-b4dd-d2ba1f1b4da5', 'Кофе в зернах', '2024-03-15 16:50:00', 'BRASILIA')
ON CONFLICT DO NOTHING;