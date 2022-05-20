# Documents

Endpoints:

- [Get documents](#get-documents)
- [Get document](#get-document)
- [Create document](#create-document)
- [Update document](#update-document)
- [Delete document](#delete-document)

Models:

- [Document model](#document-model)

## Get documents

- `GET /documents.json` will return a [paginated list](../README.md#pagination) of documents.

<!--
_Optional query parameters_:

* `attribute1` - when set to true, will only return resources that...
* `attribute2` - when set to true, will only return resources that...
-->

See the [Document model](#document-model) for more info on the response payload.

## Get document

- `GET /documents/1.json` will return the document with an ID of `1`.

See the [Document model](#document-model) for more info on the response payload.

## Create document

- `POST /documents.json` creates document.

<!--
**Required parameters**:

* `name` - name of the document.
* `description` - description of the document.
* `document_type` - document type of the document.
* `expiration_date` - expiration date of the document.
* `emission_date` - emision date of the document.
* `user_id` - user of the document.
-->

_Optional parameters_:

* `name` - name of the document.
* `description` - description of the document.
* `document_type` - document type of the document.
* `expiration_date` - expiration date of the document.
* `emission_date` - emision date of the document.
* `user_id` - user of the document.

This endpoint will return `201 Created` with the current JSON representation of the document if the creation was a success. See the [Document model](#document-model) for more info on the payload.

## Update document

- `PUT /documents/1.json` allows changing the document with an ID of `1`.

You may change any of the required or optional parameters as listed in the [create document](#create-document) endpoint.

This endpoint will return `200 OK` with the current JSON representation of the document if the update was a success. See the [Document model](#document-model) for more info on the payload.

## Delete document

- `DELETE /documents/1.json` will delete the document with an ID of `1`.

This endpoint will return `204 No Content` if successful. No parameters are required.

## Models

### Document model

```json
{
  "id": "integer",
  "name": "string",
  "description": "string",
  "document_type": "integer",
  "expiration_date": "datetime",
  "emission_date": "datetime",
  "user_id": "integer",
  "created_at": "datetime",
  "updated_at": "datetime"
}
```
