create table blog_post
(
	id uuid,
	title varchar,
	content text,
	tags varchar,
	author varchar,
	published_at timestamptz,
	created_at timestamptz
);

create unique index blog_post_id_uindex
	on blog_post (id);

alter table blog_post
	add constraint blog_post_pk
		primary key (id);
