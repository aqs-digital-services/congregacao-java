-- V1__Initial_Setup.sql

-- 1. Criar a extensão pg_trgm para índices trigram no PostgreSQL
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- 2. Tabela privilegios
CREATE TABLE privilegios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- 3. Tabela grupos sem as foreign keys para pessoas
CREATE TABLE grupos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    local_saida_campo VARCHAR(255),
    superintendente_id BIGINT,
    ajudante_id BIGINT
);

-- 4. Tabela pessoas
CREATE TABLE pessoas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    nome_familia VARCHAR(255),
    endereco VARCHAR(255),
    numero VARCHAR(50),
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    cep VARCHAR(20),
    municipio VARCHAR(255),
    uf VARCHAR(2),
    telefone1 VARCHAR(20),
    telefone2 VARCHAR(20),
    nascimento DATE,
    batismo DATE,
    grupo_id BIGINT,
    email VARCHAR(255),
    anciao BOOLEAN DEFAULT FALSE,
    servo_ministerial BOOLEAN DEFAULT FALSE,
    pioneiro_regular BOOLEAN DEFAULT FALSE,
    ungido BOOLEAN DEFAULT FALSE,
    genero VARCHAR(10),
    contato_nome VARCHAR(255),
    contato_parentesco VARCHAR(255),
    contato_telefone VARCHAR(20),
    contato_telefone1 VARCHAR(20),
    contato_email VARCHAR(255)
);

-- 5. Adicionar restrições de chave estrangeira para grupos
ALTER TABLE grupos
    ADD CONSTRAINT fk_superintendente
        FOREIGN KEY(superintendente_id)
            REFERENCES pessoas(id),
    ADD CONSTRAINT fk_ajudante
        FOREIGN KEY(ajudante_id)
            REFERENCES pessoas(id);

-- 6. Adicionar restrição de chave estrangeira para pessoas referente a grupos
ALTER TABLE pessoas
    ADD CONSTRAINT fk_grupo
        FOREIGN KEY(grupo_id)
            REFERENCES grupos(id);

-- 7. Tabela pessoa_privilegio (Relacionamento ManyToMany)
CREATE TABLE pessoa_privilegio (
    pessoa_id BIGINT NOT NULL,
    privilegio_id BIGINT NOT NULL,
    PRIMARY KEY (pessoa_id, privilegio_id),
    CONSTRAINT fk_pessoa_privilegio_pessoa
        FOREIGN KEY(pessoa_id)
            REFERENCES pessoas(id),
    CONSTRAINT fk_pessoa_privilegio_privilegio
        FOREIGN KEY(privilegio_id)
            REFERENCES privilegios(id)
);

-- 8. Tabela atividades atualizada
CREATE TABLE atividades (
    id SERIAL PRIMARY KEY,
    pessoa_id BIGINT NOT NULL,
    ano_mes VARCHAR(7) NOT NULL, -- Formato YYYY-MM
    participou_pregacao BOOLEAN NOT NULL,
    quantidade_estudos_biblicos INT NOT NULL CHECK (quantidade_estudos_biblicos >= 0),
    pioneiro_auxiliar BOOLEAN NOT NULL,
    horas INT,
    observacoes VARCHAR(500),
    CONSTRAINT fk_atividade_pessoa
        FOREIGN KEY(pessoa_id)
            REFERENCES pessoas(id),
    CONSTRAINT unique_pessoa_ano_mes
        UNIQUE(pessoa_id, ano_mes)
);

-- 9. Definir o tipo ENUM para TipoReuniao
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'tipo_reuniao_enum') THEN
        CREATE TYPE tipo_reuniao_enum AS ENUM ('MEIO_DE_SEMANA', 'FIM_DE_SEMANA');
    END IF;
END$$;

-- 10. Tabela assistencias atualizada
CREATE TABLE assistencias (
    id SERIAL PRIMARY KEY,
    tipo_reuniao tipo_reuniao_enum NOT NULL DEFAULT 'MEIO_DE_SEMANA',
    quantidade_participantes INT NOT NULL CHECK (quantidade_participantes >= 0)
    -- Não há referência a pessoa
);

-- 11. Tabela usuarios
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- 12. Índices para melhorar performance nas buscas de nomes
CREATE INDEX idx_pessoas_nome_trgm ON pessoas USING gin (nome gin_trgm_ops);
CREATE INDEX idx_pessoas_nome_familia_trgm ON pessoas USING gin (nome_familia gin_trgm_ops);

-- 13. Índices para os campos frequentemente usados
CREATE INDEX idx_pessoas_grupo_id ON pessoas (grupo_id);
CREATE INDEX idx_pessoas_genero ON pessoas (genero);

-- 14. Índices para outros relacionamentos
CREATE INDEX idx_grupos_superintendente_id ON grupos (superintendente_id);
CREATE INDEX idx_grupos_ajudante_id ON grupos (ajudante_id);
