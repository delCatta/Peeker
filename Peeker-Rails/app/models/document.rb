class Document < ApplicationRecord
  belongs_to :user
  enum document_type: [:passport, :dni]
  has_one_attached :document
  acts_as_taggable_on :tags
end
