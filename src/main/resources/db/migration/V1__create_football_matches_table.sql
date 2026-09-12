CREATE TABLE football_matches
(
    id                       UUID         PRIMARY KEY,
    competition_code         VARCHAR(50)  NOT NULL,
    home_team                VARCHAR(150) NOT NULL,
    away_team                VARCHAR(150) NOT NULL,
    kick_off_at              TIMESTAMPTZ  NOT NULL,
    status                   VARCHAR(20)  NOT NULL,
    source_provider          VARCHAR(100) NOT NULL,
    source_external_match_id VARCHAR(150) NOT NULL,
    source_fetched_at        TIMESTAMPTZ  NOT NULL,

    CONSTRAINT uk_football_matches_source
        UNIQUE (source_provider, source_external_match_id),

    CONSTRAINT chk_football_matches_distinct_teams
        CHECK (LOWER(BTRIM(home_team)) <> LOWER(BTRIM(away_team))),

    CONSTRAINT chk_football_matches_status
        CHECK (
            status IN (
                       'SCHEDULED',
                       'LIVE',
                       'FINISHED',
                       'POSTPONED',
                       'CANCELED'
                )
            )
);

CREATE INDEX idx_football_matches_kick_off_at
    ON football_matches (kick_off_at);