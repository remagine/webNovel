-- Table: public.member

-- DROP TABLE public.member;

CREATE TABLE public.member
(
  email character varying(500), -- 사용자 계정 이메일
  password character varying(1000), -- 사용자 계정 암호
  user_id character varying(500), -- 사이트 닉네임
  full_name character varying(500), -- 사용자 실명
  sex character varying(2), -- 성별
  id integer NOT NULL DEFAULT nextval('member_id_seq'::regclass), -- id pk
  state character varying(10),
  CONSTRAINT member_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

COMMENT ON COLUMN public.member.email IS '사용자 계정 이메일';
COMMENT ON COLUMN public.member.password IS '사용자 계정 암호';
COMMENT ON COLUMN public.member.user_id IS '사이트 닉네임';
COMMENT ON COLUMN public.member.full_name IS '사용자 실명';
COMMENT ON COLUMN public.member.sex IS '성별';
COMMENT ON COLUMN public.member.id IS 'id pk';