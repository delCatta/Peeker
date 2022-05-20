class Document < ApplicationRecord
  belongs_to :user
  enum document_type: [:passport, :dni]
end
