-- Table: public.chapter

-- DROP TABLE public.chapter;

CREATE TABLE public.chapter
(
  id integer NOT NULL DEFAULT nextval('chapter_id_seq'::regclass), -- pk
  title character varying(1000) NOT NULL, -- 제목
  body text NOT NULL, -- 챕터 본문
  story integer NOT NULL, -- 스토리 Fk
  create_at timestamp with time zone DEFAULT now(), -- 작성일자
  views integer DEFAULT 0, -- 조회수
  CONSTRAINT chapter_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

COMMENT ON COLUMN public.chapter.id IS 'pk';
COMMENT ON COLUMN public.chapter.title IS '제목';
COMMENT ON COLUMN public.chapter.body IS '챕터 본문';
COMMENT ON COLUMN public.chapter.story IS '스토리 Fk';
COMMENT ON COLUMN public.chapter.create_at IS '작성일자';
COMMENT ON COLUMN public.chapter.views IS '조회수';
