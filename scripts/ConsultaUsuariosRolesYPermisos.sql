use spring_security;

select u.username, r.role_name, p.name
from e_user u
inner join user_role ur on ur.user_id = u.id
inner join e_role r on r.id = ur.role_id
inner join role_permission rp on rp.role_id = r.id
inner join e_permission p on p.id = rp.permission_id
order by u.username, r.role_name, p.name


