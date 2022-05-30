json.extract! document, :id, :name, :description, :document_type, :favorite, :expiration_date, :emission_date, :user_id,
              :created_at, :updated_at
json.document_url document.document.attached? ? url_for(document.document) : ""
json.tags document.tags, :id, :name, :taggings_count
json.url document_url(document, format: :json)
