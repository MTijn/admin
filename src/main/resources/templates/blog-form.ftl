<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-CuOF+2SnTUfTwSZjCXf01h7uYhfOBuxIhGKPbfEJ3+FqH/s6cIFN9bGr1HmAg4fQ" crossorigin="anonymous">
    <link href="/admin.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-popRpmFF9JQgExhfw5tZT4I9/CI5e2QcuUZPOVXb1m7qUmeR2b50u+YFEYe1wgzy" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://cdn.ckeditor.com/ckeditor5/24.0.0/classic/ckeditor.js"></script>
    <script>
        $(function () {
            ClassicEditor
                .create(document.querySelector("#content"))
                .catch(error => {
                    console.error(error);
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
            <h1>New blog post</h1>
            <form
                    <#if blogPost??>
                        action="/blog-posts/edit/${blogPost.id}"
                    <#else>
                        action="/blog-posts"
                    </#if>

                    method="post"
            >
                <div class="form-group">
                    <label class="form-label" for="title">Title</label>
                    <input type="text" class="form-control" id="title" name="title" <#if blogPost??>value="${blogPost.title}"</#if>>
                </div>
                <div class="form-group">
                    <label class="form-label" for="content">Content</label>
                    <textarea class="form-control" id="content" name="content"><#if blogPost??>${blogPost.content}</#if></textarea>
                </div>
                <div class="form-group">
                    <label class="form-label" for="tags">Tags</label>
                    <input type="text" class="form-control" id="tags" name="tags" <#if blogPost??>value="${blogPost.tags}"</#if>>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="published" id="published">
                    <label class="form-check-label" for="published">Published</label>
                </div>
                <div class="btn-group">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button class="btn btn-primary" type="submit">Save</button>
                </div>
            </form>
        </main>
    </div>
</div>
</body>
</html>
