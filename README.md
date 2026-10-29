# MPClub: Club Management System

A small club management web app built on the classic Java web stack: **Servlets, JSP, JDBC and Tomcat**, with no frameworks. Members can register, admins can view, edit and remove members, and anyone can browse or add to a shared book catalog.

I built it to understand what frameworks like Spring normally hide: pooling database connections, handling form posts, forwarding requests between servlets and JSPs, and stitching pages together from shared fragments.

## Features

- **Member registration** with name, email, phone, program and year
- **Admin console** to list, edit and remove members (`MPMemberAdminController`)
- **Book catalog** that you can view and add to, stored in a flat file (`WEB-INF/books.txt`)
- **Central error page**: any uncaught exception is routed to `MPError.jsp` through `web.xml`
- **JUnit tests** for the `Member` model and the database layer

## Architecture

```
Browser ──► Servlets (club.Admin)          request handling, validation, routing
              │
              ├──► club.business            Member, Book (plain Java beans)
              │
              └──► club.data
                     ├─ MemberDB            JDBC with prepared statements ──► MySQL
                     ├─ ConnectionPool      JNDI DataSource from context.xml
                     └─ BookIO              reads/writes books.txt
              │
              ▼
           JSP views (web/*.jsp) + shared banner/footer fragments
```

All SQL uses `PreparedStatement` parameters, so user input is never concatenated into queries, which protects against SQL injection. Connections come from a container-managed pool rather than being opened per request.

## Running it locally

**You'll need:** JDK 21, Apache Tomcat 9 (the app uses the `javax.servlet` API), MySQL 8, and NetBeans or Ant.

1. **Create the database:**
   ```bash
   mysql -u root -p < schema.sql
   ```
2. **Set your DB credentials** in `web/META-INF/context.xml` (`username` / `password`).
3. **Build and deploy:** open the project in NetBeans and press Run, or build `dist/MPClub.war` with Ant and drop it into Tomcat's `webapps/` folder.
4. **Open** http://localhost:8080/MPClub

| Page | URL |
| --- | --- |
| Home | `/MPClub/` |
| Register a member | `/MPClub/MPRegister.jsp` |
| Manage members | `/MPClub/MPMemberAdmin?action=displayMembers` |
| Book catalog | `/MPClub/MPDisplayBooks` |

## Project layout

```
src/java/club/   Servlets, business objects, data access
web/             JSP pages, CSS, images, web.xml, context.xml
test/            JUnit tests
lib/             JDBC driver and Tomcat connection-pool jars
schema.sql       Database setup
```
