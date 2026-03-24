-- V2__seed_data.sql

-- ============================================================
-- USERS  (password = BCrypt of "Password1!")
-- ============================================================
INSERT INTO users (email, password, first_name, last_name, role, bio, is_active) VALUES
('admin@blog.com',   '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Alice',   'Admin',    'ADMIN', 'Platform administrator.',          TRUE),
('john@blog.com',    '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'John',    'Doe',      'USER',  'Full-stack dev. Coffee addict.',   TRUE),
('jane@blog.com',    '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Jane',    'Smith',    'USER',  'Spring Boot enthusiast.',          TRUE),
('mike@blog.com',    '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Mike',    'Johnson',  'USER',  'Backend engineer, love Postgres.', TRUE),
('sara@blog.com',    '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Sara',    'Lee',      'USER',  'DevOps and cloud infrastructure.', TRUE),
('tom@blog.com',     '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Tom',     'Brown',    'USER',  'Open source contributor.',         TRUE),
('emily@blog.com',   '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Emily',   'Davis',    'USER',  'Technical writer and blogger.',    TRUE),
('chris@blog.com',   '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Chris',   'Wilson',   'USER',  'Security researcher.',             TRUE),
('lisa@blog.com',    '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Lisa',    'Martinez', 'USER',  'Data engineer and SQL nerd.',      TRUE),
('david@blog.com',   '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'David',   'Garcia',   'USER',  'Microservices and DDD advocate.',  TRUE),
('nina@blog.com',    '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Nina',    'Taylor',   'USER',  'Frontend turned backend dev.',     TRUE);

-- ============================================================
-- TAGS
-- ============================================================
INSERT INTO tags (name, slug, description) VALUES
('Spring Boot',   'spring-boot',   'Everything about Spring Boot framework.'),
('Java',          'java',          'Core Java tips and best practices.'),
('PostgreSQL',    'postgresql',    'Postgres features, tuning, and tricks.'),
('Security',      'security',      'Auth, JWT, OAuth2, and secure coding.'),
('DevOps',        'devops',        'CI/CD, Docker, Kubernetes, and cloud.'),
('jOOQ',          'jooq',          'Type-safe SQL with jOOQ.'),
('Hibernate',     'hibernate',     'JPA and Hibernate ORM deep dives.'),
('REST API',      'rest-api',      'Designing and building REST APIs.'),
('Testing',       'testing',       'Unit, integration, and e2e testing.'),
('Architecture',  'architecture',  'System design and software architecture.');

-- ============================================================
-- POSTS
-- ============================================================
INSERT INTO posts (author_id, title, slug, content, summary, status, view_count, published_at) VALUES

-- Alice (admin) - 2 posts
(1, 'Welcome to the Blog Platform',
    'welcome-to-the-blog-platform',
    '## Welcome\nThis platform is built with Spring Boot, jOOQ, and PostgreSQL. Explore, write, and share your knowledge.',
    'An introduction to the blog platform and its features.',
    'PUBLISHED', 312, NOW() - INTERVAL '30 days'),

(1, 'Platform Moderation Guidelines',
    'platform-moderation-guidelines',
    '## Guidelines\nAll posts are subject to review. Be respectful, stay on topic, and avoid plagiarism.',
    'Rules and guidelines for all platform users.',
    'PUBLISHED', 198, NOW() - INTERVAL '28 days'),

-- John
(2, 'Getting Started with Spring Boot 3',
    'getting-started-with-spring-boot-3',
    '## Spring Boot 3\nSpring Boot 3 introduces native compilation support via GraalVM, Jakarta EE 10, and more.',
    'A practical intro to what is new in Spring Boot 3.',
    'PUBLISHED', 540, NOW() - INTERVAL '25 days'),

(2, 'My Draft on Spring Profiles',
    'my-draft-on-spring-profiles',
    '## Spring Profiles\nWork in progress — covering @Profile, application-{env}.yml, and activation strategies.',
    'Draft covering Spring profile configuration.',
    'DRAFT', 0, NULL),

-- Jane
(3, 'JWT Authentication in Spring Security',
    'jwt-authentication-in-spring-security',
    '## JWT Auth\nLearn how to implement a custom OncePerRequestFilter to validate JWT tokens in Spring Security.',
    'Step-by-step JWT filter implementation with Spring Security.',
    'PUBLISHED', 890, NOW() - INTERVAL '22 days'),

-- Mike
(4, 'PostgreSQL Index Strategies',
    'postgresql-index-strategies',
    '## Indexes\nCovering B-tree, GIN, BRIN, and partial indexes. Know when to use each for query performance.',
    'A guide to choosing the right Postgres index type.',
    'PUBLISHED', 430, NOW() - INTERVAL '20 days'),

-- Sara
(5, 'Dockerizing a Spring Boot App',
    'dockerizing-a-spring-boot-app',
    '## Docker\nMulti-stage Dockerfile for Spring Boot. Reduce image size, use layered JARs, and configure health checks.',
    'How to write an efficient Dockerfile for Spring Boot.',
    'PUBLISHED', 375, NOW() - INTERVAL '18 days'),

-- Tom
(6, 'Contributing to Open Source: A Beginners Guide',
    'contributing-to-open-source-beginners-guide',
    '## Open Source\nFinding good first issues, forking, branching, writing meaningful PRs, and handling review feedback.',
    'Everything you need to make your first open source contribution.',
    'PUBLISHED', 610, NOW() - INTERVAL '15 days'),

-- Emily
(7, 'Writing Better API Documentation',
    'writing-better-api-documentation',
    '## API Docs\nGood documentation includes examples, error responses, and edge cases. Tools: Swagger, Redoc, Postman.',
    'Tips for writing API docs developers actually want to read.',
    'PUBLISHED', 280, NOW() - INTERVAL '12 days'),

-- Chris
(8, 'Common Spring Security Misconfigurations',
    'common-spring-security-misconfigurations',
    '## Security\nCSRF disabled globally, permitting all actuator endpoints, storing secrets in code — avoid these mistakes.',
    'Security pitfalls to avoid in Spring Boot applications.',
    'PUBLISHED', 720, NOW() - INTERVAL '10 days'),

-- Lisa
(9, 'Window Functions in PostgreSQL',
    'window-functions-in-postgresql',
    '## Window Functions\nROW_NUMBER, RANK, LAG, LEAD, and running totals with OVER(PARTITION BY ...). Practical examples included.',
    'A deep dive into Postgres window functions with real query examples.',
    'PUBLISHED', 490, NOW() - INTERVAL '8 days'),

-- David
(10, 'Domain-Driven Design with Spring Boot',
    'domain-driven-design-with-spring-boot',
    '## DDD\nAggregates, value objects, repositories, and bounded contexts implemented in a Spring Boot monolith.',
    'Applying DDD tactical patterns in a Spring Boot project.',
    'PUBLISHED', 360, NOW() - INTERVAL '5 days'),

-- Nina
(11, 'From React to Spring: A Frontend Devs Journey',
    'from-react-to-spring-frontend-devs-journey',
    '## Backend Journey\nLearning Spring Boot coming from a React background — what clicked, what did not, and what surprised me.',
    'A frontend developer shares their experience learning Spring Boot.',
    'PUBLISHED', 510, NOW() - INTERVAL '2 days');

-- ============================================================
-- POST_TAGS
-- ============================================================
INSERT INTO post_tags (post_id, tag_id) VALUES
(1,  1), (1,  8),               -- welcome → Spring Boot, REST API
(2,  8),                        -- moderation → REST API
(3,  1), (3,  2),               -- spring boot 3 → Spring Boot, Java
(4,  1),                        -- draft → Spring Boot
(5,  1), (5,  4),               -- jwt → Spring Boot, Security
(6,  3),                        -- postgres indexes → PostgreSQL
(7,  5),                        -- docker → DevOps
(8,  2), (8,  8),               -- open source → Java, REST API
(9,  8),                        -- api docs → REST API
(10, 1), (10, 4),               -- security misconfig → Spring Boot, Security
(11, 3),                        -- window functions → PostgreSQL
(12, 1), (12, 10),              -- DDD → Spring Boot, Architecture
(13, 1), (13, 2);               -- frontend journey → Spring Boot, Java

-- ============================================================
-- COMMENTS
-- ============================================================
INSERT INTO comments (post_id, author_id, parent_id, content) VALUES
-- On "Getting Started with Spring Boot 3" (post 3)
(3, 3, NULL,  'Great intro! The GraalVM native section was especially helpful.'),
(3, 4, NULL,  'Does this work with Hibernate 6 out of the box?'),
(3, 2, 2,     'Yes, Spring Boot 3 ships with Hibernate 6 by default.'),
(3, 5, NULL,  'Bookmarked. Looking forward to the profiles follow-up draft.'),

-- On "JWT Authentication in Spring Security" (post 5)
(5, 2, NULL,  'Solid implementation. I would also add token revocation via a blocklist.'),
(5, 8, NULL,  'Make sure your secret is at least 256 bits for HS256!'),
(5, 3, 6,     'Good point — I am using a 512-bit secret generated with SecureRandom.'),

-- On "PostgreSQL Index Strategies" (post 6)
(6, 9, NULL,  'BRIN indexes are so underrated for time-series tables.'),
(6, 4, 8,     'Agreed — massive size savings compared to B-tree on append-only data.'),

-- On "Common Spring Security Misconfigurations" (post 10)
(10, 3, NULL, 'The actuator endpoint one caught me out in production once. Good reminder.'),
(10, 2, NULL, 'Should probably mention disabling default error details in prod too.'),
(10, 8, 11,   'Added that to the follow-up post I am drafting now.');

-- ============================================================
-- POST_LIKES
-- ============================================================
INSERT INTO post_likes (user_id, post_id) VALUES
(2,  1), (3,  1), (4,  1), (5,  1),
(1,  3), (3,  3), (4,  3), (5,  3), (6,  3), (9,  3),
(1,  5), (2,  5), (4,  5), (6,  5), (7,  5), (8,  5), (9,  5), (10, 5),
(2,  6), (3,  6), (5,  6), (7,  6), (9,  6),
(1,  8), (2,  8), (3,  8), (4,  8), (9,  8), (10, 8),
(2, 10), (3, 10), (4, 10), (5, 10), (6, 10), (7, 10), (11, 10);

-- ============================================================
-- COMMENT_LIKES
-- ============================================================
INSERT INTO comment_likes (user_id, comment_id) VALUES
(2, 1), (4, 1), (5, 1),
(3, 5), (4, 5), (7, 5),
(2, 6), (3, 6), (9, 6),
(4, 8), (5, 8), (10, 8);