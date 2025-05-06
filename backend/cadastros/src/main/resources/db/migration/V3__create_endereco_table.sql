CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE enderecos(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    cep VARCHAR(8) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    numero VARCHAR(100) NOT NULL,
    complemento VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    usuario_id UUID not null,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);