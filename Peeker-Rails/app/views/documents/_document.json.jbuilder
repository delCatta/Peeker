json.extract! document, :id, :name, :description, :document_type, :favorite, :expiration_date, :emission_date, :user_id,
              :created_at, :updated_at
json.file_url document.file.attached? ? url_for(document.file) : ""
json.tags document.tags, :id, :name, :taggings_count
json.url document_url(document, format: :json)
