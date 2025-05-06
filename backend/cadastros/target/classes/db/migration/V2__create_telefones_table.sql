CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE telefones(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    telefone VARCHAR(20) NOT NULL,
    usuario_id UUID not null,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE

);