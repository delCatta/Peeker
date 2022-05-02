json.extract! document, :id, :name, :description, :document_type, :expiration_date, :emision_date, :user_id, :created_at, :updated_at
json.url document_url(document, format: :json)
