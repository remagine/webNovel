-- Table: public.story

-- DROP TABLE public.story;

CREATE TABLE public.story
(
  id integer NOT NULL DEFAULT nextval('story_id_seq'::regclass),
  title character varying(1000) NOT NULL, -- 팬픽 제목
  description text NOT NULL, -- 팬픽 설명
  foreword text NOT NULL, -- 머릿말
  "character" character varying(1000), -- 등장인물
  co_author character varying(1000), -- 공동저자
  cover_image character varying(1000), -- 표지 이미지
  views integer DEFAULT 0, -- 조회수
  state character varying(10) DEFAULT 'on'::character varying, -- 소설 게시물 상태
  create_at timestamp with time zone DEFAULT now(), -- 생성일시
  member integer NOT NULL, -- 멤버 id 참조키
  tag character varying(1000), -- tag
  CONSTRAINT story_pkey PRIMARY KEY (id),
  CONSTRAINT story_member_id_fkey FOREIGN KEY (member)
      REFERENCES public.a_member (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

COMMENT ON COLUMN public.story.title IS '팬픽 제목';
COMMENT ON COLUMN public.story.description IS '팬픽 설명';
COMMENT ON COLUMN public.story.foreword IS '머릿말';
COMMENT ON COLUMN public.story."character" IS '등장인물';
COMMENT ON COLUMN public.story.co_author IS '공동저자
';
COMMENT ON COLUMN public.story.cover_image IS '표지 이미지';
COMMENT ON COLUMN public.story.views IS '조회수';
COMMENT ON COLUMN public.story.state IS '소설 게시물 상태';
COMMENT ON COLUMN public.story.create_at IS '생성일시';
COMMENT ON COLUMN public.story.member IS '멤버 id 참조키';
COMMENT ON COLUMN public.story.tag IS 'tag ';
