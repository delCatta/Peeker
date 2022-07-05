json.extract! tag, :id, :name, :user_id, :created_at, :updated_at
json.documents tag.documents, partial: 'documents/document', as: :document
json.url tag_url(tag, format: :json)
