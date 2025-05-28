INSERT INTO coffee.coffee (id, name, create_date, region)
VALUES ('9ba5f1b2-fc3c-42aa-b4dd-d2ba1f1b4da5', 'Кофе в зернах', '2024-03-15 16:00:00', 'BRASILIA')
ON CONFLICT DO NOTHING;

INSERT INTO coffee.coffee (id, name, create_date, region)
VALUES ('7d6114d5-2583-443d-b095-124e7627639a', 'Кофе молотый', '2024-03-15 16:30:30', 'ETHIOPIA')
ON CONFLICT DO NOTHING;

INSERT INTO coffee.coffee (id, name, create_date, region)
VALUES ('bccded07-b414-4f32-97f8-bca9cce02173', 'ТЫ', '2024-03-15 17:00:00', 'ХУЙЛО')
ON CONFLICT DO NOTHING;