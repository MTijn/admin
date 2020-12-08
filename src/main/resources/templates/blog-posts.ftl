<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-CuOF+2SnTUfTwSZjCXf01h7uYhfOBuxIhGKPbfEJ3+FqH/s6cIFN9bGr1HmAg4fQ" crossorigin="anonymous">
    <link href="admin.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-popRpmFF9JQgExhfw5tZT4I9/CI5e2QcuUZPOVXb1m7qUmeR2b50u+YFEYe1wgzy" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script type="application/javascript">
        $(function() {
            $("#exampleModal").on("show.bs.modal", function (event) {
                let id = $(event.relatedTarget).data("id");
                $("#delete").on("click", function (event) {
                    $.ajax({
                        type: "delete",
                        url: "/blog-posts/" + id,
                        success: function () {
                            $("#" + id).remove();
                        },
                        error: function () {
                            alert("Could not delete " + id)
                        }
                    })
                });
            });
        });
    </script>
</head>
<body>
<#include "header.ftl">
<div class="container-fluid">
    <div class="row">
        <#include "sidebar.ftl">
        <main class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <h1>Blog posts</h1>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Tags</th>
                        <th>Created at</th>
                        <th><a class="btn btn-sm btn-primary" href="/blog-posts/new">New</a></th>
                    </tr>
                </thead>
                <tbody>
                    <#list blogPosts as blogPost>
                        <tr id="${blogPost.id}">
                            <td>${blogPost.title}</td>
                            <td>${blogPost.tags}</td>
                            <td>${blogPost.createdAt.format('dd-MM-yyyy HH:mm:ss Z')}</td>
                            <td>
                                <div class="btn-group">
                                    <a href="/blog-posts/edit/${blogPost.id}" class="btn btn-warning btn-sm">Edit</a>
                                    <button class="btn btn-danger btn-sm delete" data-toggle="modal" data-target="#exampleModal" data-id="${blogPost.id}">Delete</button>
                                </div>
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </main>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Delete</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"> <span aria-hidden="true">&times;</span> </button>
            </div>
            <div class="modal-body">Are you sure you want to delete this post?</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button id="delete" class="btn btn-danger">Delete</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
