-- Schema of the database

-- Representation of a cpu
create table if not exists cpulist (
	cpulist_id bigint primary key,
	cpulist_name varchar(255) not null
);

-- Representation of a person
create table if not exists users (
	users_id bigint primary key,
	users_name varchar(255) not null,
	users_passwd varchar(255) not null
);

-- Stores the ranking a person gave to a cpu; both person and cpu are stored in the tables above
-- users can make multiple rankings, but a ranking can only correspond to one user 
-- one cpus can be ranked several times, but a ranking can only correspond to one cpu 
create table if not exists userscpuranking (
	userscpuranking_cpuid bigint,
	userscpuranking_usersid bigint,
	userscpuranking_ranking integer not null,
	foreign key userscpuranking_cpuid references cpulist(cpulist_id),
	foreign key userscpuranking_usersid references users(users_id),
	primary key(userscpuranking_cpuid, userscpuranking_usersid)
);

-- Table to quickly compute the average ranking per cpu
-- each summary corresponds uniquely to one cpu and each cpu can only have one summary (otherwise, data duplication has occured)
create table if not exists cpurankingsummary (
	cpurankingsummary_cpuid bigint primary key,
	cpurankingsummary_ranksum integer not null,
	cpurankingsummary_count integer not null,
	foreign key cpurankingsummary_cpuid references cpulist(cpulist_id)
);